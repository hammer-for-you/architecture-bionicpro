import React, {useState} from 'react';
import {useKeycloak} from '@react-keycloak/web';

const ReportPage: React.FC = () => {
  const {keycloak, initialized} = useKeycloak();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const defaultFromDate = '2025-10-21T00:00';
  const defaultToDate = '2025-10-23T00:00';

  const [fromDate, setFromDate] = useState<string>(defaultFromDate);
  const [toDate, setToDate] = useState<string>(defaultToDate);

  const downloadReport = async () => {
    if (!keycloak?.token) {
      setError('Not authenticated');
      return;
    }

    try {
      setLoading(true);
      setError(null);

      const url = new URL(`${process.env.REACT_APP_API_URL}/reports`);

      if (fromDate) {
        url.searchParams.append('from', fromDate + ':00');
      }

      if (toDate) {
        url.searchParams.append('to', toDate + ':00');
      }

      const response = await fetch(url.toString(), {
        headers: {
          'Authorization': `Bearer ${keycloak.token}`
        }
      });

      if (!response.ok) {
        setError(`HTTP error ${response.status}!`);
        return;
      }

      const blob = await response.blob();
      const contentDisposition = response.headers.get('Content-Disposition');
      let filename = 'report.txt';

      if (contentDisposition) {
        const filenameMatch = contentDisposition.match(/filename="?(.+)"?/);
        if (filenameMatch) {
          filename = filenameMatch[1];
        }
      }

      const downloadUrl = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = downloadUrl;
      link.download = filename;
      document.body.appendChild(link);
      link.click();

      document.body.removeChild(link);
      window.URL.revokeObjectURL(downloadUrl);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'An error occurred');
    } finally {
      setLoading(false);
    }
  };

  const handleFromDateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFromDate(e.target.value);
  };

  const handleToDateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setToDate(e.target.value);
  };

  const clearFilters = () => {
    setFromDate('');
    setToDate('');
  };

  const resetToDefault = () => {
    setFromDate(defaultFromDate);
    setToDate(defaultToDate);
  };

  if (!initialized) {
    return <div>Loading...</div>;
  }

  if (!keycloak.authenticated) {
    return (
      <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
        <button
          onClick={() => keycloak.login()}
          className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
        >
          Login
        </button>
      </div>
    );
  }

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
      <div className="p-8 bg-white rounded-lg shadow-md">
        <h1 className="text-2xl font-bold mb-6">Usage Reports</h1>

        <div className="mb-6 space-y-4">
          <div>
            <label htmlFor="fromDate" className="block text-sm font-medium text-gray-700 mb-1">
              From Date & Time
            </label>
            <input
              type="datetime-local"
              id="fromDate"
              value={fromDate || ''}
              onChange={handleFromDateChange}
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label htmlFor="toDate" className="block text-sm font-medium text-gray-700 mb-1">
              To Date & Time
            </label>
            <input
              type="datetime-local"
              id="toDate"
              value={toDate}
              onChange={handleToDateChange}
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div className="flex space-x-2">
            <button
              onClick={clearFilters}
              className="flex-1 px-3 py-2 bg-gray-200 text-gray-700 rounded hover:bg-gray-300 text-sm"
            >
              Clear Filters
            </button>
            <button
              onClick={resetToDefault}
              className="flex-1 px-3 py-2 bg-blue-100 text-blue-700 rounded hover:bg-blue-200 text-sm"
            >
              Reset to Default
            </button>
          </div>
        </div>

        <button
          onClick={downloadReport}
          disabled={loading}
          className={`px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 ${
            loading ? 'opacity-50 cursor-not-allowed' : ''
          }`}
        >
          {loading ? 'Generating Report...' : 'Download Report'}
        </button>

        {error && (
          <div className="mt-4 p-4 bg-red-100 text-red-700 rounded">
            {error}
          </div>
        )}
      </div>
    </div>
  );
};

export default ReportPage;
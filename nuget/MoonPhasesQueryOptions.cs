using System;
using System.Collections.Generic;
using System.Text;
using Newtonsoft.Json;

namespace APIVerve.API.MoonPhases
{
    /// <summary>
    /// Query options for the Moon Phases API
    /// </summary>
    public class MoonPhasesQueryOptions
    {
        /// <summary>
        /// The date for which you want to get the moon phase (e.g., MM-DD-YYYY : 01-01-2022)
        /// Example: 12-02-2025
        /// </summary>
        [JsonProperty("date")]
        public string Date { get; set; }
    }
}
